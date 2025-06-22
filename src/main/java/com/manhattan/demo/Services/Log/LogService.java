package com.manhattan.demo.Services.Log;

import com.manhattan.demo.Entities.Log.LogEntity;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Repositories.Log.LogRepository;
import com.manhattan.demo.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    public void registrar(String usuarioId, String acao, String detalhes){
        UserEntity usuario = userRepository.findById(usuarioId).orElseThrow(() ->new RuntimeException("Usuário não encontrado"));

        LogEntity log = new LogEntity();
        log.setUserEntity(usuario);
        log.setAcao(acao);
        log.setDetalhes(detalhes);
        log.setDataHora(LocalDateTime.now());

        logRepository.save(log);
    }
}
