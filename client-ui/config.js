const config = {
  development: {
    baseUrl: "http://localhost:8011",
    dockerBaseUrl: "http://localhost:8011",
    androidBaseUrl: "http://localhost:8011",
    uiUrl: "http://localhost:3000",
  },
  production: {
    baseUrl: "https://api.justyoga.fit",
    dockerBaseUrl: "http://gateway:8011",
    androidBaseUrl: "https://api.justyoga.fit",
    uiUrl: "https://justyoga.fit",
  },
};

const env = process.env.NODE_ENV;
export default config[env];
