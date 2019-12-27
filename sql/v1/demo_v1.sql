CREATE TABLE IF EXISTS users (
    id VARCHAR(32) PRIMARY KEY,
    username VARCHAR(32),
    phone VARCHAR(14),
    authentication VARCHAR(32)
);

INSERT INTO users VALUE('1', '老王', '13966666666', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO users VALUE('2', '老许', '13975784715', 'e10adc3949ba59abbe56e057f20f883e');