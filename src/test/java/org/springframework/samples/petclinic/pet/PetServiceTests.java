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
package org.springframework.samples.petclinic.pet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceTests {

    @Mock
    private PetRepository pets;

    @InjectMocks
    private PetService petService;

    @Test
    public void shouldFindPetTypes() {
        PetType petType = new PetType();
        given(this.pets.findPetTypes()).willReturn(Collections.singletonList(petType));

        List<PetType> result = this.petService.findPetTypes();

        assertThat(result).containsExactly(petType);
    }

    @Test
    public void shouldFindPetById() {
        Pet pet = new Pet();
        given(this.pets.findById(1)).willReturn(pet);

        Pet result = this.petService.findById(1);

        assertThat(result).isSameAs(pet);
    }

    @Test
    public void shouldSavePet() {
        Pet pet = new Pet();

        this.petService.save(pet);

        verify(this.pets).save(pet);
    }

}
