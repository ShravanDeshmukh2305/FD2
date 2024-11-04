package com.example.FD.Aggregator.service;

import com.example.FD.Aggregator.dto.MpinDto;
import com.example.FD.Aggregator.entity.Mpin;
import com.example.FD.Aggregator.exceptions.ResourceNotFoundException;
import com.example.FD.Aggregator.repository.MpinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MpinService {

    @Autowired
    private MpinRepository mpinRepository;

    public Mpin createMpin(MpinDto mpinDto) {
        Mpin mpin = new Mpin();
        mpin.setRefNo(mpinDto.getRefId());
        mpin.setUserRefId(mpinDto.getUserRefId());
        mpin.setUuid(mpinDto.getUuid());
        mpin.setMpin(mpinDto.getMpin());
        mpin.setIsLocked(mpinDto.getIsLocked());
        mpin.setRetry(mpinDto.getRetry());
        mpin.setCreatedAt(LocalDateTime.now());
        mpin.setUpdatedAt(LocalDateTime.now());
        return mpinRepository.save(mpin);
    }

    public Optional<Mpin> getMpinById(Long id) {
        return mpinRepository.findById(id);
    }

    public Mpin updateMpin(Long id, MpinDto mpinDto) {
        Mpin mpin = mpinRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MPIN not found"));
        mpin.setMpin(mpinDto.getMpin());
        mpin.setIsLocked(mpinDto.getIsLocked());
        mpin.setRetry(mpinDto.getRetry());
        mpin.setUpdatedAt(LocalDateTime.now());
        return mpinRepository.save(mpin);
    }

    public void deleteMpin(Long id) {
        mpinRepository.deleteById(id);
    }
}
