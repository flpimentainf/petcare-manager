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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OwnerServiceTests {

    @Mock
    private OwnerRepository owners;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    public void shouldFindOwnersByLastName() {
        Owner owner = new Owner();
        given(this.owners.findByLastName("Franklin")).willReturn(Collections.singletonList(owner));

        Collection<Owner> result = this.ownerService.findByLastName("Franklin");

        assertThat(result).containsExactly(owner);
    }

    @Test
    public void shouldFindOwnerById() {
        Owner owner = new Owner();
        given(this.owners.findById(1)).willReturn(owner);

        Owner result = this.ownerService.findById(1);

        assertThat(result).isSameAs(owner);
    }

    @Test
    public void shouldSaveOwner() {
        Owner owner = new Owner();

        this.ownerService.save(owner);

        verify(this.owners).save(owner);
    }

}
