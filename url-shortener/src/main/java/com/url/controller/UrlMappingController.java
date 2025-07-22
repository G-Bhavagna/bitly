package com.url.controller;

import com.url.dtos.ClickEventDTO;
import com.url.dtos.UrlMappingDTO;
import com.url.models.UrlMapping;
import com.url.models.User;
import com.url.service.UrlMappingService;
import com.url.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    private final UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String,String> request, Principal principal){

        String originalUrl=request.get("originalUrl");
        User user=userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO=urlMappingService.createShortUrl(originalUrl,user);
        return ResponseEntity.ok(urlMappingDTO);
    }

    @PostMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
        User user=userService.findByUsername(principal.getName());
        List<UrlMappingDTO> urlMappingDTOS=urlMappingService.getUrlsByUsername(user);
        return ResponseEntity.ok(urlMappingDTOS);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnlystics(@PathVariable String shortUrl, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start=LocalDateTime.parse(startDate, formatter);
        LocalDateTime end=LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> clickEventDTOS=urlMappingService.getClickEventsByDate(shortUrl,start,end);
        return ResponseEntity.ok(clickEventDTOS);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate,Long>> getTotalClicksByDate(Principal principal, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        User user=userService.findByUsername(principal.getName());
        LocalDate start=LocalDate.parse(startDate, formatter);
        LocalDate end=LocalDate.parse(endDate, formatter);
        Map<LocalDate,Long> totalCliks=urlMappingService.getTotalClicksByUserAndDate(user,start,end);
        return ResponseEntity.ok(totalCliks);
    }

}
