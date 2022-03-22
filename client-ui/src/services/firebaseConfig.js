const config = {
  development: {
    apiKey: "AIzaSyARUd-8gcBzIM41BSFPGnr6yyhWxjphcUs",
    authDomain: "credo-f7d83.firebaseapp.com",
    databaseURL: "https://credo-f7d83.firebaseio.com",
    projectId: "credo-f7d83",
    storageBucket: "credo-f7d83.appspot.com",
    messagingSenderId: "916941463029"
  },
  production: {
    apiKey: "AIzaSyA5pVXw4FTPx1Hpa23QL_Vfu0W9Q353qCs",
    authDomain: "justyoga-842f9.firebaseapp.com",
    databaseURL: "https://justyoga-842f9.firebaseio.com",
    projectId: "justyoga-842f9",
    storageBucket: "justyoga-842f9.appspot.com",
    messagingSenderId: "187071230957",
    appId: "1:187071230957:web:5b14665c5b18106aeb2de4",
    measurementId: "G-YEYR3PW3LF"
  }
};

const env = process.env.NODE_ENV;
export default config[env];
