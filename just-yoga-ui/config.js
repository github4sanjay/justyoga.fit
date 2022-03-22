const config = {
  development: {
    baseUrl: "http://localhost:8011",
    dockerBaseUrl: "http://localhost:8011",
    uiUrl: "http://localhost:3000",
  },
  production: {
    baseUrl: "http://localhost:8011",
    dockerBaseUrl: "http://gateway:8011",
    uiUrl: "http://localhost:3000",
  },
};

const env = process.env.NODE_ENV;
module.exports = config[env];
