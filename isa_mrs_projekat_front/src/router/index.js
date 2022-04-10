import { createRouter, createWebHistory } from "vue-router";
import AdminMainView from "@/components/Administrator/AdminMainView/AdminMainView";
import ProfileView from "@/components/GeneralComponents/UserAccountForms/ProfileView";
import TheAdminSidebar from "@/components/Administrator/AdminPage/components/TheAdminSidebar/TheAdminSidebar";
import AdminDashboard from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminDashboard";
import PasswordChangeForm from "@/components/GeneralComponents/UserAccountForms/PasswordChangeForm";
import FishingInstructorPage from "@/components/FishingInstructor/FishingInstructorPage/FishingInstructorPage";
import AccountDeletionForm from "@/components/GeneralComponents/UserAccountForms/AccountDeletionForm";
//import InitView from "@/components/UnregisteredUser/InitView";
import TheUnregisteredUserSidebar from "@/components/UnregisteredUser/TheUnregisteredUserSidebar";
import store from "@/store";
import TheFishingInstructorSidebar from "@/components/FishingInstructor/FishingInstructorPage/components/TheFishingInstructorSidebar/TheFishingInstructorSidebar";
import OwnerContentPage from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/OwnerContentPage/OwnerContentPage";
import TheOwnerSidebar from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/TheOwnerSidebar/TheOwnerSidebar";
import BoatOwnerContentPage from "@/components/BoatOwner/BoatOwnerPage/components/BoatOwnerMainView/BoatOwnerContentPage";
import TheBoatOwnerSidebar from "@/components/BoatOwner/BoatOwnerPage/components/TheBoatOwnerSidebar/TheBoatOwnerSidebar";
// import AdminRequestsView from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRequestsView/AdminRequestsView";
// import AdminRequestPreview
//     from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRequestsView/AdminRequestPreview/AdminRequestPreview";
import AdminRequestsView
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRequestsView/AdminRequestsView";

const routes = [
    {
        path: "/",
        name: "InitHome",
        component: AdminMainView
        components: {
            default: AdminRequestsView,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        }
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