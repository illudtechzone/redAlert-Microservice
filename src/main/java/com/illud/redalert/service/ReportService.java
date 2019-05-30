package com.illud.redalert.service;

import com.illud.redalert.service.dto.ReportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Report.
 */
public interface ReportService {

    /**
     * Save a report.
     *
     * @param reportDTO the entity to save
     * @return the persisted entity
     */
    ReportDTO save(ReportDTO reportDTO);

    /**
     * Get all the reports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReportDTO> findAll(Pageable pageable);


    /**
     * Get the "id" report.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReportDTO> findOne(Long id);

    /**
     * Delete the "id" report.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
