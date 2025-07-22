package com.url.service;

import com.url.dtos.ClickEventDTO;
import com.url.dtos.UrlMappingDTO;
import com.url.models.ClickEvent;
import com.url.models.UrlMapping;
import com.url.models.User;
import com.url.repo.ClickEventRepository;
import com.url.repo.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;
    public UrlMappingDTO createShortUrl(String originalUrl, User user) {

        String shortUrl=generateShortUrl();
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedAt(LocalDateTime.now());
        UrlMapping saved=urlMappingRepository.save(urlMapping);

        return convertToDto(saved);

    }

    private UrlMappingDTO convertToDto(UrlMapping saved) {
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
        urlMappingDTO.setId(saved.getId());
        urlMappingDTO.setOriginalUrl(saved.getOriginalUrl());
        urlMappingDTO.setShortUrl(saved.getShortUrl());
        urlMappingDTO.setCreatedAt(saved.getCreatedAt());
        urlMappingDTO.setUsername(saved.getUser().getUsername());
        urlMappingDTO.setClickCount(saved.getClickCount());

        return urlMappingDTO;
    }
    private String generateShortUrl() {
        String characters="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();

        StringBuilder shortUrl=new StringBuilder(8);
        for(int i=0;i<8;i++){
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }

    public List<UrlMappingDTO> getUrlsByUsername(User user) {
        List<UrlMapping> urlMappings=urlMappingRepository.findByUser(user);
        List<UrlMappingDTO> urlMappingDTOS=new ArrayList<>();
        for(UrlMapping urlMapping:urlMappings){
            urlMappingDTOS.add(convertToDto(urlMapping));
        }
        return urlMappingDTOS;
    }

    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        List<ClickEventDTO> clickEventDTOS=new ArrayList<>();
        UrlMapping urlMapping=urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping!=null){
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping,start,end)
                    .stream().collect(Collectors.groupingBy(click->click.getClickDate().toLocalDate(),Collectors.counting()))
                    .entrySet().stream()
                    .map(entry->{
                        ClickEventDTO clickEventDTO=new ClickEventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    }).collect(Collectors.toList());
        }
        return clickEventDTOS;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings=urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents=clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings,start.atStartOfDay(),end.plusDays(1).atStartOfDay());
        return clickEvents.stream().collect(Collectors.groupingBy(click->click.getClickDate().toLocalDate(),Collectors.counting()));

    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping= urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping!=null){
            urlMapping.setClickCount(urlMapping.getClickCount()+1);
            urlMappingRepository.save(urlMapping);

            // Record CLick Event
            ClickEvent clickEvent=new ClickEvent();
            clickEvent.setUrlMapping(urlMapping);
            clickEvent.setClickDate(LocalDateTime.now());
            clickEventRepository.save(clickEvent);
        }
        return urlMapping;
    }
}
