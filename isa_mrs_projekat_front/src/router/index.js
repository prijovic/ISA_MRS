import { createRouter, createWebHistory } from "vue-router";
import AdminMainView from "@/components/Administrator/AdminMainView/AdminMainView";
import BoatOwnerMainView from "@/components/BoatOwner/BoatOwnerMainView/BoatOwnerMainView";

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
    },
    {
        path: "/boatOwner",
        name: "BoatOwnerMainView",
        component: BoatOwnerMainView
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router