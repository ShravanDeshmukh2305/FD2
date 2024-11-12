package com.example.FD.Aggregator.service;

import com.example.FD.Aggregator.dto.CreateMpinRequestDTO;
import com.example.FD.Aggregator.dto.CreateMpinResponseDTO;
import com.example.FD.Aggregator.dto.MpinDto;
import com.example.FD.Aggregator.entity.Mpin;
import com.example.FD.Aggregator.entity.User;
import com.example.FD.Aggregator.exceptions.ResourceNotFoundException;
import com.example.FD.Aggregator.repository.MpinRepository;
import com.example.FD.Aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MpinService {

    @Autowired
    private MpinRepository mpinRepository;

    @Autowired
    private UserRepository userRepository;

    public CreateMpinResponseDTO createMpin(CreateMpinRequestDTO mpinDto, String userRefId) {
        User user = userRepository.findByRefNo(userRefId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userRefId));

        Mpin mpin = new Mpin();
        mpin.setUser(user);
        mpin.setGuid(mpinDto.getGuid());
        mpin.setMpin(mpinDto.getMpin());
        mpin.setIsLocked(false);
        mpin.setRetry(0);
        mpin.setCreatedAt(LocalDateTime.now());
        mpin.setUpdatedAt(LocalDateTime.now());
        mpin.setOsType(mpinDto.getOsType());
        mpin.setOsVersion(mpinDto.getOsVersion());
        mpin.setDeviceBrand(mpinDto.getDeviceBrand());
        mpin.setDeviceModel(mpinDto.getDeviceModel());
        mpin.setAppVersion(mpinDto.getAppVersion());
        mpin.setLoginDate(mpinDto.getLoginDate());

        Mpin savedMpin = mpinRepository.save(mpin);

        CreateMpinResponseDTO.SuccessData successData = new CreateMpinResponseDTO.SuccessData();
        CreateMpinResponseDTO.SuccessData.MpinData mpinData = new CreateMpinResponseDTO.SuccessData.MpinData();

        successData.setSuccessCode("200");
        successData.setSuccessMsg("MPIN created successfully");

        mpinData.setRefId(savedMpin.getRefId());
        mpinData.setUserRefId(savedMpin.getUser().getRefNo());
        mpinData.setGuid(savedMpin.getGuid());
        mpinData.setMpin(savedMpin.getMpin());
        mpinData.setIsLocked(savedMpin.getIsLocked());
        mpinData.setRetry(savedMpin.getRetry());
        mpinData.setCreatedAt(savedMpin.getCreatedAt().toString());
        mpinData.setUpdatedAt(savedMpin.getUpdatedAt().toString());
        mpinData.setOsType(savedMpin.getOsType());
        mpinData.setOsVersion(savedMpin.getOsVersion());
        mpinData.setDeviceBrand(savedMpin.getDeviceBrand());
        mpinData.setDeviceModel(savedMpin.getDeviceModel());
        mpinData.setAppVersion(savedMpin.getAppVersion());
        mpinData.setLoginDate(savedMpin.getLoginDate().toString());

        successData.setData(mpinData);
        CreateMpinResponseDTO response = new CreateMpinResponseDTO();
        response.setSuccess(successData);
        response.setError(null);

        return response;
    }






    // Get MPIN by user reference number
    public Optional<Mpin> getMpinByUserRefNo(String refNo) {
        return mpinRepository.findByUser_RefNo(refNo); // Make sure this method is defined in the repository
    }

    // Update MPIN
    public Mpin updateMpin(Long id, MpinDto mpinDto) {
        Mpin mpin = mpinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MPIN not found with ID: " + id));

        User user = userRepository.findByRefNo(mpinDto.getUserRefId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + mpinDto.getUserRefId()));

        mpin.setUser(user);
        mpin.setMpin(mpinDto.getMpin());
        mpin.setIsLocked(mpinDto.getIsLocked());
        mpin.setRetry(mpinDto.getRetry());
        mpin.setUpdatedAt(LocalDateTime.now());

        return mpinRepository.save(mpin);
    }

    // Delete MPIN by ID
    public void deleteMpin(Long id) {
        if (!mpinRepository.existsById(id)) {
            throw new ResourceNotFoundException("MPIN not found with ID: " + id);
        }
        mpinRepository.deleteById(id);
    }
}
