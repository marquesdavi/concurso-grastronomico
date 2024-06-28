package com.gastronomic.contest.repository;

import com.gastronomic.contest.domain.Vote;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vote entity.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    default Optional<Vote> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Vote> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Vote> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select vote from Vote vote left join fetch vote.customer left join fetch vote.dish",
        countQuery = "select count(vote) from Vote vote"
    )
    Page<Vote> findAllWithToOneRelationships(Pageable pageable);

    @Query("select vote from Vote vote left join fetch vote.customer left join fetch vote.dish")
    List<Vote> findAllWithToOneRelationships();

    @Query("select vote from Vote vote left join fetch vote.customer left join fetch vote.dish where vote.id =:id")
    Optional<Vote> findOneWithToOneRelationships(@Param("id") UUID id);

    @Query("select count(v) > 0 from Vote v where v.customer.id = :customerId and v.dish.id = :dishId")
    boolean existsVoteByCustomerAndDish(@Param("customerId") Long customerId, @Param("dishId") Long dishId);
}
