-- Initial users setup
INSERT OR IGNORE INTO users (username, password, role) VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00dmxs.TVuHOn2', 'ROLE_ADMIN');
INSERT OR IGNORE INTO users (username, password, role) VALUES ('faculty', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.S8qZ36ET1pA0z.Z6', 'ROLE_FACULTY');
