package com.hacof.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacof.hackathon.entity.Device;
import com.hacof.hackathon.entity.Feedback;
import com.hacof.hackathon.entity.HackathonResult;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.entity.MentorshipRequest;
import com.hacof.hackathon.entity.MentorshipSessionRequest;
import com.hacof.hackathon.entity.SponsorshipHackathon;
import com.hacof.hackathon.entity.TeamHackathon;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.UserHackathon;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonDTO {
    String id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title;

    @NotBlank(message = "Sub Title is required")
    @Size(max = 250, message = "Sub Title must be less than 250 characters")
    String subTitle;

    @NotBlank(message = "Banner Image URL is required")
    @URL(message = "Banner Image URL must be a valid URL")
    String bannerImageUrl;

    @NotNull(message = "Enroll Start Date is required")
    @FutureOrPresent(message = "Enroll Start Date must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime enrollStartDate;

    @NotNull(message = "Enroll End Date is required")
    @FutureOrPresent(message = "Enroll End Date must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime enrollEndDate;

    @NotNull(message = "Max Teams is required")
    int enrollmentCount;

    @NotNull(message = " Start Date is required")
    @FutureOrPresent(message = "Start Date must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime startDate;

    @NotNull(message = " End Date is required")
    @FutureOrPresent(message = " End Date must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime endDate;

    @NotBlank(message = "Information is required")
    String information;

    String description;

    @NotBlank(message = "Contact is required")
    String contact;

    // @NotBlank(message = "Category is required")
    String category; // Used for category filtering

    // @NotBlank(message = "Organization is required")
    String organization;

    String enrollmentStatus; // OPEN - UP_COMING - CLOSED

    @NotNull(message = "Hackathon Status is required")
    String status; // DRAFT - OPEN - ON_GOING - CLOSED

    @Min(value = 1, message = "Minimum Team Members must be greater than 0")
    @Max(value = 5, message = "Minimum Team Members must be less than 5")
    int minimumTeamMembers; // minTeamSize

    @Min(value = 1, message = "Maximum Team Members must be greater than 0")
    @Max(value = 5, message = "Maximum Team Members must be less than 5")
    int maximumTeamMembers; // maxTeamSize

    List<String> documentation; // Document public URLs

    @JsonIgnore
    List<Long> roundIds;

    @JsonIgnore
    List<TeamHackathon> teamHackathons;

    @JsonIgnore
    List<HackathonResult> hackathonResults;

    @JsonIgnore
    List<UserHackathon> userHackathons;

    @JsonIgnore
    List<TeamRequest> teamRequests;

    @JsonIgnore
    List<IndividualRegistrationRequest> individualRegistrationRequests;

    @JsonIgnore
    List<MentorshipRequest> mentorshipRequests;

    @JsonIgnore
    List<MentorshipSessionRequest> mentorshipSessionRequests;

    @JsonIgnore
    List<SponsorshipHackathon> sponsorshipHackathons;

    @JsonIgnore
    List<Device> devices;

    @JsonIgnore
    List<Feedback> feedbacks;

    // Audit fields
    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;

    @AssertTrue(message = "Start Date must be before End Date")
    private boolean isEndDateAfterStartDate() {
        return enrollEndDate == null || enrollStartDate == null || enrollEndDate.isAfter(enrollStartDate);
    }

    @AssertTrue(message = "Minimum Team Members must be less than or equal to Maximum Team Members")
    private boolean isMaxTeamSizeValid() {
        return maximumTeamMembers >= minimumTeamMembers;
    }

    @Override
    public String toString() {
        return "HackathonDTO{" + "id='"
                + id + '\'' + ", title='"
                + title + '\'' + ", subTitle='"
                + subTitle + '\'' + ", bannerImageUrl='"
                + bannerImageUrl + '\'' + ", enrollStartDate="
                + enrollStartDate + ", enrollEndDate="
                + enrollEndDate + ", enrollmentCount="
                + enrollmentCount + ", startDate="
                + startDate + ", endDate="
                + endDate + ", information='"
                + information + '\'' + ", description='"
                + description + '\'' + ", contact='"
                + contact + '\'' + ", category='"
                + category + '\'' + ", organization='"
                + organization + '\'' + ", enrollmentStatus='"
                + enrollmentStatus + '\'' + ", status='"
                + status + '\'' + ", minimumTeamMembers="
                + minimumTeamMembers + ", maximumTeamMembers="
                + maximumTeamMembers + ", documentation="
                + documentation + ", roundIds="
                + roundIds + ", teamHackathons="
                + teamHackathons + ", hackathonResults="
                + hackathonResults + ", userHackathons="
                + userHackathons + ", teamRequests="
                + teamRequests + ", individualRegistrationRequests="
                + individualRegistrationRequests + ", mentorshipRequests="
                + mentorshipRequests + ", mentorshipSessionRequests="
                + mentorshipSessionRequests + ", sponsorshipHackathons="
                + sponsorshipHackathons + ", devices="
                + devices + ", feedbacks="
                + feedbacks + ", createdByUserName='"
                + createdByUserName + '\'' + ", createdAt="
                + createdAt + ", lastModifiedByUserName='"
                + lastModifiedByUserName + '\'' + ", updatedAt="
                + updatedAt + '}';
    }
}
