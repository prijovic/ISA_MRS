import { createRouter, createWebHistory } from "vue-router";
import AdminMainView from "@/components/Administrator/AdminMainView/AdminMainView";
import OwnerMainView from "@/components/VacationRentalOwner/OwnerMainView/OwnerMainView";

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
        path: "/vacationRentalOwner",
        name: "OwnerMainView",
        component: OwnerMainView
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router