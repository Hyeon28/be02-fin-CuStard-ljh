import { createRouter, createWebHistory } from 'vue-router'
import QnaRegisterPage from '../page/QnaRegisterPage.vue'
import QnaListPage from '../page/QnaListPage.vue'
import QnaListReadPage from '../page/QnaListReadPage'

const routes = [
  { path: '/qna/list', component: QnaListPage },
  { path: '/qna/register', component: QnaRegisterPage },
  { path: '/qna/read/:idx', component: QnaListReadPage}
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
