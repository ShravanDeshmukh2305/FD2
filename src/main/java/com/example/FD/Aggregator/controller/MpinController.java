package com.example.FD.Aggregator.controller;

import com.example.FD.Aggregator.dto.MpinDto;
import com.example.FD.Aggregator.entity.Mpin;
import com.example.FD.Aggregator.exceptions.ResourceNotFoundException;
import com.example.FD.Aggregator.service.MpinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/mpin")
public class MpinController {

    @Autowired
    private MpinService mpinService;

    @PostMapping
    public ResponseEntity<Mpin> createMpin(@RequestBody MpinDto mpinDto) {
        Mpin createdMpin = mpinService.createMpin(mpinDto);
        return ResponseEntity.ok(createdMpin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mpin> getMpinById(@PathVariable Long id) {
        Mpin mpin = mpinService.getMpinById(id).orElseThrow(() -> new ResourceNotFoundException("MPIN not found"));
        return ResponseEntity.ok(mpin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mpin> updateMpin(@PathVariable Long id, @RequestBody MpinDto mpinDto) {
        Mpin updatedMpin = mpinService.updateMpin(id, mpinDto);
        return ResponseEntity.ok(updatedMpin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMpin(@PathVariable Long id) {
        mpinService.deleteMpin(id);
        return ResponseEntity.noContent().build();
    }
}
