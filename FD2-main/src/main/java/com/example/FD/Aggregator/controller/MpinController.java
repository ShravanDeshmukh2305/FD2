package com.example.FD.Aggregator.controller;

import com.example.FD.Aggregator.dto.CreateMpinRequestDTO;
import com.example.FD.Aggregator.dto.CreateMpinResponseDTO;
import com.example.FD.Aggregator.dto.MpinDto;
import com.example.FD.Aggregator.entity.Mpin;
import com.example.FD.Aggregator.exceptions.ResourceNotFoundException;
import com.example.FD.Aggregator.service.MpinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/mpin")
public class MpinController {

    private final MpinService mpinService;

    public MpinController(MpinService mpinService) {
        this.mpinService = mpinService;
    }

    @PostMapping
    public ResponseEntity<CreateMpinResponseDTO> createMpin(
            @RequestBody CreateMpinRequestDTO mpinDto,
            @RequestHeader("userRefId") String userRefId) {

        try {
            CreateMpinResponseDTO response = mpinService.createMpin(mpinDto, userRefId);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new CreateMpinResponseDTO(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreateMpinResponseDTO(null, "An error occurred"));
        }
    }


    @GetMapping("/user/{refNo}")
    public ResponseEntity<Mpin> getMpinByUserRefNo(@PathVariable String refNo) {
        Optional<Mpin> mpin = mpinService.getMpinByUserRefNo(refNo);
        return mpin.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
