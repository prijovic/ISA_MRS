import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import "bootstrap"
import "./axios/index";
import store from "./store/index"
import Notifications from "@kyvg/vue3-notification"
import "v-calendar/dist/style.css"

createApp(App)
    .use(router)
    .use(store)
    .use(Notifications)
    .mount('#app')
