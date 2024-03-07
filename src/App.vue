<template>
    <div class="main">
      <router-view></router-view>
    </div>
  </template>
  
  <script>
  
  export default {
    name: "App",
    components: {
      
    },
  };
  </script>
  
  <style>
  #modalLogin {
      display: none;
  }
  #modalSignup {
      display: none;
  }
  .main {
      /* height: 500px; */
      text-align: center;
  }
  </style>
  