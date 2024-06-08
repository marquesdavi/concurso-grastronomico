package com.gastronomic.contest.web.rest;

import com.gastronomic.contest.domain.Vote;
import com.gastronomic.contest.repository.VoteRepository;
import com.gastronomic.contest.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gastronomic.contest.domain.Vote}.
 */
@RestController
@RequestMapping("/api/votes")
@Transactional
public class VoteResource {

    private final Logger log = LoggerFactory.getLogger(VoteResource.class);

    private static final String ENTITY_NAME = "vote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoteRepository voteRepository;

    public VoteResource(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    /**
     * {@code POST  /votes} : Create a new vote.
     *
     * @param vote the vote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vote, or with status {@code 400 (Bad Request)} if the vote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vote> createVote(@Valid @RequestBody Vote vote) throws URISyntaxException {
        log.debug("REST request to save Vote : {}", vote);
        if (vote.getId() != null) {
            throw new BadRequestAlertException("A new vote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vote = voteRepository.save(vote);
        return ResponseEntity.created(new URI("/api/votes/" + vote.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vote.getId().toString()))
            .body(vote);
    }

    /**
     * {@code PUT  /votes/:id} : Updates an existing vote.
     *
     * @param id the id of the vote to save.
     * @param vote the vote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vote,
     * or with status {@code 400 (Bad Request)} if the vote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable(value = "id", required = false) final UUID id, @Valid @RequestBody Vote vote)
        throws URISyntaxException {
        log.debug("REST request to update Vote : {}, {}", id, vote);
        if (vote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vote = voteRepository.save(vote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vote.getId().toString()))
            .body(vote);
    }

    /**
     * {@code PATCH  /votes/:id} : Partial updates given fields of an existing vote, field will ignore if it is null
     *
     * @param id the id of the vote to save.
     * @param vote the vote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vote,
     * or with status {@code 400 (Bad Request)} if the vote is not valid,
     * or with status {@code 404 (Not Found)} if the vote is not found,
     * or with status {@code 500 (Internal Server Error)} if the vote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vote> partialUpdateVote(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Vote vote
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vote partially : {}, {}", id, vote);
        if (vote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vote> result = voteRepository.findById(vote.getId()).map(voteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vote.getId().toString())
        );
    }

    /**
     * {@code GET  /votes} : get all the votes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Vote>> getAllVotes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Votes");
        Page<Vote> page;
        if (eagerload) {
            page = voteRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = voteRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /votes/:id} : get the "id" vote.
     *
     * @param id the id of the vote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVote(@PathVariable("id") UUID id) {
        log.debug("REST request to get Vote : {}", id);
        Optional<Vote> vote = voteRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(vote);
    }

    /**
     * {@code DELETE  /votes/:id} : delete the "id" vote.
     *
     * @param id the id of the vote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Vote : {}", id);
        voteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
