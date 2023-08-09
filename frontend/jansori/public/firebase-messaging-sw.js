importScripts(
  "https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js"
);
importScripts(
  "https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js"
);

var firebaseConfig = {
  apiKey: "AIzaSyBZ_cNYLGG9mzG5nUrnSsCacq6QuEQo-cY",
  authDomain: "jansori-393804.firebaseapp.com",
  projectId: "jansori-393804",
  storageBucket: "jansori-393804.appspot.com",
  messagingSenderId: "591770825704",
  appId: "1:591770825704:web:e3432cf2a2674a0cb32a5d",
};

const firebaseapp = firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging(firebaseapp);
