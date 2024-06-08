package com.gastronomic.contest.web.rest;

import static com.gastronomic.contest.domain.DishAsserts.*;
import static com.gastronomic.contest.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastronomic.contest.IntegrationTest;
import com.gastronomic.contest.domain.Dish;
import com.gastronomic.contest.repository.DishRepository;
import jakarta.persistence.EntityManager;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DishResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DishResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_RESTAURANT = "AAAAAAAAAA";
    private static final String UPDATED_RESTAURANT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dishes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDishMockMvc;

    private Dish dish;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dish createEntity(EntityManager em) {
        Dish dish = new Dish()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .restaurant(DEFAULT_RESTAURANT);
        return dish;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dish createUpdatedEntity(EntityManager em) {
        Dish dish = new Dish()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .restaurant(UPDATED_RESTAURANT);
        return dish;
    }

    @BeforeEach
    public void initTest() {
        dish = createEntity(em);
    }

    @Test
    @Transactional
    void createDish() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dish
        var returnedDish = om.readValue(
            restDishMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dish)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Dish.class
        );

        // Validate the Dish in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDishUpdatableFieldsEquals(returnedDish, getPersistedDish(returnedDish));
    }

    @Test
    @Transactional
    void createDishWithExistingId() throws Exception {
        // Create the Dish with an existing ID
        dish.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDishMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dish)))
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dish.setTitle(null);

        // Create the Dish, which fails.

        restDishMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dish)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dish.setDescription(null);

        // Create the Dish, which fails.

        restDishMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dish)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRestaurantIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dish.setRestaurant(null);

        // Create the Dish, which fails.

        restDishMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dish)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDishes() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        // Get all the dishList
        restDishMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dish.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].restaurant").value(hasItem(DEFAULT_RESTAURANT)));
    }

    @Test
    @Transactional
    void getDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        // Get the dish
        restDishMockMvc
            .perform(get(ENTITY_API_URL_ID, dish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dish.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.restaurant").value(DEFAULT_RESTAURANT));
    }

    @Test
    @Transactional
    void getNonExistingDish() throws Exception {
        // Get the dish
        restDishMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dish
        Dish updatedDish = dishRepository.findById(dish.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDish are not directly saved in db
        em.detach(updatedDish);
        updatedDish
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .restaurant(UPDATED_RESTAURANT);

        restDishMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDish.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDish))
            )
            .andExpect(status().isOk());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDishToMatchAllProperties(updatedDish);
    }

    @Test
    @Transactional
    void putNonExistingDish() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dish.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDishMockMvc
            .perform(put(ENTITY_API_URL_ID, dish.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dish)))
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDish() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dish.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDishMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dish))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDish() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dish.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDishMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dish)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDishWithPatch() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dish using partial update
        Dish partialUpdatedDish = new Dish();
        partialUpdatedDish.setId(dish.getId());

        partialUpdatedDish.title(UPDATED_TITLE);

        restDishMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDish.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDish))
            )
            .andExpect(status().isOk());

        // Validate the Dish in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDishUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDish, dish), getPersistedDish(dish));
    }

    @Test
    @Transactional
    void fullUpdateDishWithPatch() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dish using partial update
        Dish partialUpdatedDish = new Dish();
        partialUpdatedDish.setId(dish.getId());

        partialUpdatedDish
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .restaurant(UPDATED_RESTAURANT);

        restDishMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDish.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDish))
            )
            .andExpect(status().isOk());

        // Validate the Dish in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDishUpdatableFieldsEquals(partialUpdatedDish, getPersistedDish(partialUpdatedDish));
    }

    @Test
    @Transactional
    void patchNonExistingDish() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dish.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDishMockMvc
            .perform(patch(ENTITY_API_URL_ID, dish.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dish)))
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDish() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dish.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDishMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dish))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDish() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dish.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDishMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dish)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dish in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dish
        restDishMockMvc
            .perform(delete(ENTITY_API_URL_ID, dish.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dishRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Dish getPersistedDish(Dish dish) {
        return dishRepository.findById(dish.getId()).orElseThrow();
    }

    protected void assertPersistedDishToMatchAllProperties(Dish expectedDish) {
        assertDishAllPropertiesEquals(expectedDish, getPersistedDish(expectedDish));
    }

    protected void assertPersistedDishToMatchUpdatableProperties(Dish expectedDish) {
        assertDishAllUpdatablePropertiesEquals(expectedDish, getPersistedDish(expectedDish));
    }
}
