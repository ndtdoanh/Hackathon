package com.hacof.hackathon.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Campuses")
public class Campus extends AuditBase {
    /**
     * Campus of FPT University : Quy Nhon - Da Nang - Can Tho - TP HCM - Hoa Lac
     * Address of Hoa Lac: Education and Training Area – Hoa Lac High-Tech Park – Km29 Thang Long Avenue, Thach That District, Hanoi Capital
     * Address of TP HCM: Lot E2a-7, Road D1 High-Tech Park, Long Thanh My Ward, Thu Duc City, Ho Chi Minh City
     * Address of Da Nang: FPT Danang Technology Urban Area, Hoa Hai Ward, Ngu Hanh Son District, Da Nang City
     * Address of Can Tho: No. 600 Nguyen Van Cu Street (extended), An Binh Ward, Ninh Kieu District, Can Tho City
     * Address of Quy Nhon: An Phu Thinh New Urban Area, Nhon Binh Ward & Dong Da Ward, Quy Nhon City, Binh Dinh Province
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String name; // Quy Nhon - Da Nang - Can Tho - TP HCM - Hoa Lac
    String location;

    @OneToMany(mappedBy = "campus")
    List<Event> events;

    @OneToMany(mappedBy = "campus")
    List<Mentor> mentors;

    @OneToMany(mappedBy = "campus")
    List<TrainingSession> trainingSessions;
}
