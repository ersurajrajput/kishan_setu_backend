package com.kishansetu.kishan_setu_backend.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropRecommandReqDTO {
    private String region;
    private String soilType;
    private String upComingSeason;
    private String waterAvailability;
}
