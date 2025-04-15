package com.hacof.hackathon.controller;

import com.hacof.hackathon.dto.FileUrlResponse;
import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.service.SponsorshipHackathonDetailService;
import com.hacof.hackathon.service.SponsorshipHackathonService;
import com.hacof.hackathon.service.SponsorshipService;
import com.hacof.hackathon.specification.SponsorshipSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sponsorships")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class SponsorshipController {
    // this class manages 3 components: Sponsorship, SponsorshipHackathon, SponsorshipHackathonDetail
    SponsorshipService sponsorshipService;
    SponsorshipHackathonService sponsorshipHackathonService;
    SponsorshipHackathonDetailService sponsorshipHackathonDetailService;

    @PostMapping
    public ResponseEntity<CommonResponse<SponsorshipDTO>> createSponsorship(
            @RequestBody @Valid CommonRequest<SponsorshipDTO> request) {
        SponsorshipDTO sponsorshipDTO = sponsorshipService.create(request.getData());
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship created successfully"),
                sponsorshipDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<SponsorshipDTO>> updateSponsorship(
            @RequestBody @Valid CommonRequest<SponsorshipDTO> request) {
        SponsorshipDTO sponsorshipDTO =
                sponsorshipService.update(request.getData().getId(), request.getData());
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship updated successfully"),
                sponsorshipDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<SponsorshipDTO>> deleteSponsorship(@PathVariable Long id) {

        sponsorshipService.delete(id);
        CommonResponse<SponsorshipDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SponsorshipDTO>>> getSponsorships(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String money) {
        Specification<Sponsorship> spec = Specification.where(SponsorshipSpecification.hasId(id));
        //                .and(SponsorshipSpecification.hasName(name))
        //                .and(SponsorshipSpecification.hasBrand(brand))
        //                .and(SponsorshipSpecification.hasContent(content))
        //                .and(SponsorshipSpecification.hasMoney(money));

        List<SponsorshipDTO> sponsorships = sponsorshipService.getAll(spec);
        CommonResponse<List<SponsorshipDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all sponsorships successfully"),
                sponsorships);
        return ResponseEntity.ok(response);
    }

    // sponsorship hackathon service
    @PostMapping("/hackathons")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> createSponsorshipHackathon(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDTO> request) {
        SponsorshipHackathonDTO sponsorshipHackathonDTO = sponsorshipHackathonService.create(request.getData());
        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship Hackathon created successfully"),
                sponsorshipHackathonDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/hackathons")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> updateSponsorshipHackathon(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDTO> request) {
        SponsorshipHackathonDTO sponsorshipHackathonDTO =
                sponsorshipHackathonService.update(request.getData().getId(), request.getData());
        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Sponsorship Hackathon updated successfully"),
                sponsorshipHackathonDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/hackathons/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> deleteSponsorshipHackathon(@PathVariable String id) {
        sponsorshipHackathonService.delete(Long.parseLong(id));
        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hackathons")
    public ResponseEntity<CommonResponse<List<SponsorshipHackathonDTO>>> getAllSponsorshipHackathons() {
        List<SponsorshipHackathonDTO> sponsorshipHackathons = sponsorshipHackathonService.getAll();
        CommonResponse<List<SponsorshipHackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all Sponsorship Hackathons successfully"),
                sponsorshipHackathons);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hackathons/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> getSponsorshipHackathonById(@PathVariable String id) {
        SponsorshipHackathonDTO sponsorshipHackathonDTO = sponsorshipHackathonService.getById(id);
        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Sponsorship Hackathon successfully"),
                sponsorshipHackathonDTO
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hackathons/by-hackathon/{hackathonId}")
    public ResponseEntity<CommonResponse<List<SponsorshipHackathonDTO>>> getSponsorshipHackathonsByHackathonId(@PathVariable String hackathonId) {
        List<SponsorshipHackathonDTO> sponsorshipHackathons = sponsorshipHackathonService.getAllByHackathonId(hackathonId);
        CommonResponse<List<SponsorshipHackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Sponsorship Hackathons by Hackathon ID successfully"),
                sponsorshipHackathons
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hackathons/by-sponsorship/{sponsorshipId}")
    public ResponseEntity<CommonResponse<List<SponsorshipHackathonDTO>>> getSponsorshipHackathonsBySponsorshipId(@PathVariable String sponsorshipId) {
        List<SponsorshipHackathonDTO> sponsorshipHackathons = sponsorshipHackathonService.getAllBySponsorshipId(sponsorshipId);
        CommonResponse<List<SponsorshipHackathonDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Sponsorship Hackathons by Sponsorship ID successfully"),
                sponsorshipHackathons
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hackathons/by-hackathon-sponsorship")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDTO>> getSponsorshipHackathonByHackathonAndSponsorshipId(
            @RequestParam String hackathonId,
            @RequestParam String sponsorshipId) {

        SponsorshipHackathonDTO sponsorshipHackathonDTO = sponsorshipHackathonService.getByHackathonAndSponsorshipId(hackathonId, sponsorshipId);

        CommonResponse<SponsorshipHackathonDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Sponsorship Hackathon by Hackathon and Sponsorship IDs successfully"),
                sponsorshipHackathonDTO
        );

        return ResponseEntity.ok(response);
    }


//    @PostMapping("/details")
//    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> createSponsorshipHackathonDetail(
//            @RequestBody @Valid CommonRequest<SponsorshipHackathonDetailDTO> request) {
//        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO =
//                sponsorshipHackathonDetailService.create(request.getData());
//        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
//                UUID.randomUUID().toString(),
//                LocalDateTime.now(),
//                "HACOF",
//                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail created successfully"),
//                sponsorshipHackathonDetailDTO);
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/details")
//    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> updateSponsorshipHackathonDetail(
//            @RequestBody @Valid CommonRequest<SponsorshipHackathonDetailDTO> request) {
//        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO = sponsorshipHackathonDetailService.update(
//                Long.parseLong(request.getData().getId()), request.getData());
//        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
//                UUID.randomUUID().toString(),
//                LocalDateTime.now(),
//                "HACOF",
//                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail updated successfully"),
//                sponsorshipHackathonDetailDTO);
//        return ResponseEntity.ok(response);
//    }


    @PostMapping("/details")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> createSponsorshipHackathonDetail(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDetailDTO> request) {
        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO =
                sponsorshipHackathonDetailService.createWithFiles(request.getData());
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail created successfully"),
                sponsorshipHackathonDetailDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/details/update-info")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> updateSponsorshipHackathonDetailInfo(
            @RequestBody @Valid CommonRequest<SponsorshipHackathonDetailDTO> request) {
        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO =
                sponsorshipHackathonDetailService.updateInfo(Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail info updated successfully"),
                sponsorshipHackathonDetailDTO);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/details/update-files/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> updateSponsorshipHackathonDetailFiles(
            @PathVariable Long id, @RequestBody CommonRequest<List<String>> request) {
        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO =
                sponsorshipHackathonDetailService.updateFiles(id, request.getData());
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail files updated successfully"),
                sponsorshipHackathonDetailDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> deleteSponsorshipHackathonDetail(
            @PathVariable String id) {
        sponsorshipHackathonDetailService.delete(Long.parseLong(id));
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Sponsorship Hackathon Detail deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details")
    public ResponseEntity<CommonResponse<List<SponsorshipHackathonDetailDTO>>> getAllSponsorshipHackathonDetails() {
        List<SponsorshipHackathonDetailDTO> sponsorshipHackathonDetails = sponsorshipHackathonDetailService.getAll();
        CommonResponse<List<SponsorshipHackathonDetailDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all Sponsorship Hackathon Details successfully"),
                sponsorshipHackathonDetails);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<CommonResponse<SponsorshipHackathonDetailDTO>> getSponsorshipHackathonDetailById(
            @PathVariable Long id) {
        SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO = sponsorshipHackathonDetailService.getById(id);
        CommonResponse<SponsorshipHackathonDetailDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Sponsorship Hackathon Detail successfully"),
                sponsorshipHackathonDetailDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/details/by-sponsorship-hackathon/{sponsorshipHackathonId}")
    public ResponseEntity<CommonResponse<List<SponsorshipHackathonDetailDTO>>> getSponsorshipHackathonDetailsBySponsorshipHackathonId(
            @PathVariable String sponsorshipHackathonId) {
        List<SponsorshipHackathonDetailDTO> sponsorshipHackathonDetails = sponsorshipHackathonDetailService.getAllBySponsorshipHackathonId(sponsorshipHackathonId);
        CommonResponse<List<SponsorshipHackathonDetailDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched Sponsorship Hackathon Details by SponsorshipHackathonId successfully"),
                sponsorshipHackathonDetails
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{sponsorshipHackathonDetailId}/file-urls")
    public ResponseEntity<CommonResponse<List<FileUrlResponse>>> getFileUrlsBysponsorshipHackathonDetailId(
            @PathVariable Long sponsorshipHackathonDetailId) {

        List<FileUrlResponse> fileUrls = sponsorshipHackathonDetailService.getFileUrlsBySponsorshipHackathonDetailId(sponsorshipHackathonDetailId);

        CommonResponse<List<FileUrlResponse>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "File URLs fetched successfully"),
                fileUrls
        );

        return ResponseEntity.ok(response);
    }

}
