package com.spring.projects.MVCRest.services;

import com.spring.projects.MVCRest.api.v1.mapper.VendorMapper;
import com.spring.projects.MVCRest.api.v1.model.VendorDTO;
import com.spring.projects.MVCRest.api.v1.model.VendorListDTO;
import com.spring.projects.MVCRest.controllers.v1.VendorController;
import com.spring.projects.MVCRest.domain.Vendor;
import com.spring.projects.MVCRest.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService{

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getVendorURL(id));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendorDTOS =  vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorURL(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
        return new VendorListDTO(vendorDTOS);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        return saveAndReturn(vendor);
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {

        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        vendor.setId(id);

        return saveAndReturn(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    //todo if more properties, add more if statements

                    if(vendorDTO.getName() != null){
                        vendor.setName(vendorDTO.getName());
                    }

                    return saveAndReturn(vendor);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {

        vendorRepository.deleteById(id);
    }

    private String getVendorURL(Long id){
        return VendorController.BASE_URL + "/" + id;
    }

    private VendorDTO saveAndReturn(Vendor vendor){
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);

        savedVendorDTO.setVendorUrl(getVendorURL(savedVendor.getId()));

        return savedVendorDTO;
    }
}
