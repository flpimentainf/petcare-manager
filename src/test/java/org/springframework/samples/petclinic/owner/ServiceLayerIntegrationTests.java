/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ServiceLayerIntegrationTests {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private PetService petService;

    @Autowired
    private VisitService visitService;

    @Test
    public void shouldFindOwnerThroughServiceLayer() {
        Owner owner = this.ownerService.findById(1);

        assertThat(owner.getLastName()).isEqualTo("Franklin");
        assertThat(owner.getPets()).hasSize(1);
    }

    @Test
    @Transactional
    public void shouldSavePetThroughServiceLayer() {
        Owner owner = this.ownerService.findById(1);
        Pet pet = new Pet();
        pet.setName("Thor");
        pet.setBirthDate(LocalDate.now());
        pet.setType(this.petService.findPetTypes().get(0));
        owner.addPet(pet);

        this.petService.save(pet);
        Owner savedOwner = this.ownerService.findById(1);

        assertThat(pet.getId()).isNotNull();
        assertThat(savedOwner.getPet("Thor", true)).isNotNull();
    }

    @Test
    @Transactional
    public void shouldSaveVisitThroughServiceLayer() {
        Pet pet = this.petService.findById(7);
        int visits = pet.getVisits().size();
        Visit visit = new Visit();
        visit.setDescription("service layer test");
        pet.addVisit(visit);

        this.visitService.save(visit);
        Pet savedPet = this.petService.findById(7);

        assertThat(visit.getId()).isNotNull();
        assertThat(savedPet.getVisits()).hasSize(visits + 1);
    }

}
