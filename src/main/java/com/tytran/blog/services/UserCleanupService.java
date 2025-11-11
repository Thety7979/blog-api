package com.tytran.blog.services;

import java.util.UUID;

public interface UserCleanupService {
    void cleanupUserData(UUID userId);
}
