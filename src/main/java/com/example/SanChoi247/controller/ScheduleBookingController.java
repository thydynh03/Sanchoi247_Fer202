package com.example.SanChoi247.controller;

import com.example.SanChoi247.model.entity.ScheduleBooking;
import com.example.SanChoi247.model.repo.ScheduleBookingRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScheduleBookingController {

    @Autowired
    private ScheduleBookingRepo scheduleBookingRepository;

    // Load the edit form with the current booking data
    @GetMapping("/editBooking/{id}")
    public String showEditBookingForm(@PathVariable("id") int bookingId, Model model) throws Exception {
        ScheduleBooking booking = scheduleBookingRepository.findById(bookingId);

        if (booking != null) {
            model.addAttribute("booking", booking); // Ensure the booking is added to the model
            return "owner/editBookingForm"; // Thymeleaf template for the edit form
        } else {
            return "redirect:/errorPage"; // Handle the case where the booking is not found
        }
    }

    @PostMapping("/editBooking")
    public String updateBooking(@ModelAttribute("booking") ScheduleBooking updatedBooking) throws Exception {
        // Get the existing booking from the database
        ScheduleBooking existingBooking = scheduleBookingRepository.findById(updatedBooking.getBooking_id());
    
        if (existingBooking == null) {
            throw new Exception("Booking not found");
        }
    
        // Update the booking with the new details from the user input
        existingBooking.setStart_time(updatedBooking.getStart_time()); // Set new start time
        existingBooking.setEnd_time(updatedBooking.getEnd_time()); // Set new end time
        existingBooking.setStatus(updatedBooking.getStatus()); // Update status
        existingBooking.setPrice(updatedBooking.getPrice()); // Update price
        existingBooking.setBooking_date(updatedBooking.getBooking_date()); // Update booking date if needed
    
        // Save the updated booking to the database
        scheduleBookingRepository.update(existingBooking);
    
        return "redirect:/"; // Redirect to the bookings list page
    }
    
    

}
