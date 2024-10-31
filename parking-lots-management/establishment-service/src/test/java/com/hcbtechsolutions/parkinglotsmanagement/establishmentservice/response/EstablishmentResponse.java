package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.response;

import java.util.List;

import org.springframework.hateoas.Link;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;

public class EstablishmentResponse {
    private Embedded _embedded;
    private Link _links;

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public Link get_links() {
        return _links;
    }

    public void set_links(Link _links) {
        this._links = _links;
    }

    public static class Embedded {
        private List<EstablishmentDto> establishmentDtoList;

        public List<EstablishmentDto> getEstablishmentDtoList() {
            return establishmentDtoList;
        }

        public void setEstablishmentDtoList(List<EstablishmentDto> establishmentDtoList) {
            this.establishmentDtoList = establishmentDtoList;
        }
    }
}
