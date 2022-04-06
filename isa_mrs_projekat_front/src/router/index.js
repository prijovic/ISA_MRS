import { createRouter, createWebHistory } from "vue-router";
import AdminMainView from "@/components/Administrator/AdminMainView/AdminMainView";

const routes = [
    {
        path: "/",
        name: "InitHome",
        component: AdminMainView
    },
    {
        path: "/admin",
        name: "AdminMainView",
        component: AdminMainView
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router