const WebSocket = require('ws');
const mysql = require('mysql');
const jwt = require('jsonwebtoken');

const JWT_SECRET = Buffer.from('sanchoi247SecureKeyForJWTSigningXyZ123456789abcdefghijklmnopqrstuv', 'utf-8');

// Thiết lập kết nối đến MySQL
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'thithithi0305',
    database: 'SanChoi247'
});

db.connect((err) => {
    if (err) {
        console.error('Database connection failed: ' + err.stack);
        return;
    }
    console.log('Connected to MySQL database.');
});

const wss = new WebSocket.Server({ port: 8081, perMessageDeflate: false });

// Lưu trữ kết nối và thông tin người dùng
const clients = new Map();

wss.on('connection', (ws, req) => {
    console.log('A new client connected!');

    const token = req.headers['sec-websocket-protocol'];

    if (!token) {
        ws.send(JSON.stringify({ type: 'error', message: 'No token provided.' }));
        ws.close();
        return;
    }

    let decoded;
    try {
        decoded = jwt.verify(token, JWT_SECRET); // Sử dụng khóa bí mật đã lấy được
    } catch (error) {
        console.error('Token verification failed:', error);
        ws.send(JSON.stringify({ type: 'error', message: 'Invalid token.' }));
        ws.close();
        return;
    }

    const username = decoded.sub; // Lấy username từ token đã giải mã

    // Truy vấn thông tin người dùng từ database
    const query = 'SELECT name, avatar FROM users WHERE username = ?';
    db.query(query, [username], (err, results) => {
        if (err) {
            console.error('Error fetching user data: ', err);
            ws.send(JSON.stringify({ type: 'error', message: 'Database error occurred' }));
            return;
        }

        if (results.length === 0) {
            console.log('User not found for username:', username);
            ws.send(JSON.stringify({ type: 'error', message: 'User not found.' }));
            return;
        }

        const user = results[0];
        clients.set(ws, { name: user.name, avatar: user.avatar });
        ws.send(JSON.stringify({ type: 'success' }));

        const welcomeMessage = `${user.name} has joined the chat!`;

        // Gửi message tới các client khác
        wss.clients.forEach((client) => {
            if (client.readyState === WebSocket.OPEN && client !== ws) {
                client.send(JSON.stringify({
                    type: 'serverMessage',
                    name: user.name,
                    message: welcomeMessage,
                    avatar: user.avatar
                }));
            }
        });
    });

    // Xử lý tin nhắn chat từ client
    ws.on('message', (data) => {
        const message = JSON.parse(data);

        if (message.type === 'chat') {
            const senderInfo = clients.get(ws);
            if (senderInfo) {
                const chatMessage = `${message.text}`;
                // Gửi tin nhắn tới tất cả các client khác
                wss.clients.forEach((client) => {
                    if (client.readyState === WebSocket.OPEN) {
                        client.send(JSON.stringify({ 
                            type: 'chat', 
                            sender: senderInfo.name, 
                            avatar: senderInfo.avatar, 
                            message: chatMessage 
                        }));
                    }
                });
            }
        }
    });

    // Xử lý khi client ngắt kết nối
    ws.on('close', () => {
        const userInfo = clients.get(ws);
        if (userInfo) {
            clients.delete(ws);

            // Gửi thông báo tới các client khác về việc người dùng rời phòng
            wss.clients.forEach((client) => {
                if (client.readyState === WebSocket.OPEN) {
                    client.send(JSON.stringify({ type: 'serverMessage', message: `${userInfo.name} has left the chat.` }));
                }
            });
            console.log(`${userInfo.name} disconnected`);
        }
    });
});

console.log('WebSocket server is running on ws://localhost:8081');
