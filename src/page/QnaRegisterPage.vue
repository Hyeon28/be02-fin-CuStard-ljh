<template>
    <div id="articlelist">
      <form @submit.prevent="submitForm">
        <label for="title">제목:</label>
        <input type="text" v-model="title" /><br /><br />
        <label for="content">내용:</label>
        <textarea v-model="content"></textarea><br /><br />
        <label for="password">비밀번호:</label>
        <input type="password" v-model="password" /><br /><br />
        <button type="submit">작성</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        title: '',
        content: '',
        password: '',
        articles: []
      };
    },
    methods: {
      submitForm() {
        axios.post("http://localhost:8080/qna/register", {
          title: this.title,
          content: this.content,
          password: this.password
        })
        .then((response) => {
          console.log("데이터 전송 성공:", response.data);
          this.title = '';
          this.content = '';
          this.password = '';
        })
        .catch((error) => {
          console.error("데이터 전송 실패:", error);
        });
      }
    }
  };
  </script>
  
  <style scoped>
  #articlelist {
    border: 1px solid #ccc;
    padding: 20px;
    max-width: 500px;
    margin: 0 auto;
  }
  
  form {
    display: flex;
    flex-direction: column;
  }
  
  label {
    margin-bottom: 5px;
  }
  
  input[type="text"],
  textarea,
  input[type="password"],
  button {
    margin-bottom: 10px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  button {
    background-color: #007bff;
    color: white;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  button:active {
    background-color: #004280;
  }
  </style>
  