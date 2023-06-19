package com.webtracker.app.dto.mapper;

import com.webtracker.app.dto.ClientDto;
import com.webtracker.app.model.Client;

public class ClientMapper {

    private ClientMapper() {
    }

    public static Client mapToClient(ClientDto clientDto) {
        return new Client(clientDto.getName(), clientDto.getEmail());
    }


}
