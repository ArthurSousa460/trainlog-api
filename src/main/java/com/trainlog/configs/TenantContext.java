package com.trainlog.configs;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.PreparedStatement;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TenantContext {

    private final EntityManager entityManager;

    public void setTenant(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("O tenant não pode ser nulo");
        }

        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new IllegalStateException(
                    "O tenant só pode ser definido dentro de uma transação"
            );
        }

        Session session = entityManager.unwrap(Session.class);

        session.doWork(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT set_config('app.current_user_id', ?, true)"
            )) {
                statement.setString(1, userId.toString());
                statement.executeQuery();
            }
        });
    }
}
