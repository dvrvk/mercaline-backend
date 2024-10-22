package com.mercaline.service;

import com.mercaline.model.StatusEntity;
import com.mercaline.repository.StatusRepository;
import com.mercaline.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService extends BaseService<StatusEntity, Long, StatusRepository> {
}
