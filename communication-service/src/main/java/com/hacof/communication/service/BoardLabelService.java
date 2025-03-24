package com.hacof.communication.service;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;

import java.util.List;

public interface BoardLabelService {

    BoardLabelResponseDTO createBoardLabel(BoardLabelRequestDTO boardLabelRequestDTO);

}
