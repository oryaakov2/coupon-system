package com.or_yaakov.couponsystem.service;

import com.or_yaakov.couponsystem.rest.common.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;

@Service
public class TokensManager {
    private static final long MILLISECONDS_IN_THIRTY_MINUTES = 1_800_000L;

    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public TokensManager(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.tokensMap = tokensMap;
    }

    public ClientSession put(String token, ClientSession session) {
        return tokensMap.put(token, session);
    }

    public <X extends Throwable> ClientSession accessOrElseThrow(String token, Supplier<? extends X> supplier) throws X {
        if (tokensMap.containsKey(token)) {
            return tokensMap.get(token).access();
        }
        throw supplier.get();
    }

    /**
     * Removes expired {@link ClientSession} from the {@link Map}.
     */
    @Scheduled(fixedDelay = MILLISECONDS_IN_THIRTY_MINUTES)
    private void removesExpiredClientSession() {
        if (tokensMap.isEmpty()) {
            return;
        }
        for (String token : tokensMap.keySet()) {
            ClientSession clientSession = tokensMap.get(token);

            if (System.currentTimeMillis() - clientSession.getLastAccessMillis() >= MILLISECONDS_IN_THIRTY_MINUTES) {
                tokensMap.remove(token);
            }
        }
    }
}