import { createRouter, createWebHistory } from "vue-router";
import ProfileView from "@/components/GeneralComponents/UserAccountForms/ProfileView";
import TheAdminNavbar from "@/components/Administrator/AdminPage/components/TheAdminNavbar/TheAdminNavbar";
import TheAdminSidebar from "@/components/Administrator/AdminPage/components/TheAdminSidebar/TheAdminSidebar";
import AdminDashboard from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminDashboard";
import PasswordChangeForm from "@/components/GeneralComponents/UserAccountForms/PasswordChangeForm";
import FishingInstructorPage from "@/components/FishingInstructor/FishingInstructorPage/FishingInstructorPage";
import AccountDeletionForm from "@/components/GeneralComponents/UserAccountForms/AccountDeletionForm";

const routes = [
    {
        path: "/",
        name: "InitHome",
        redirect: "/admin"
    },
    {
        path: "/admin",
        name: "AdminDashboard",
        components: {
            default: AdminDashboard,
            navbar: TheAdminNavbar,
            sidebar: TheAdminSidebar,
        }
    },
    {
        path: "/admin/changePass",
        name: "AdminChangePassword",
        components: {
            default: PasswordChangeForm,
            navbar: TheAdminNavbar,
            sidebar: TheAdminSidebar,
        }
    },
    {
        path: "/admin/delAcc",
        name: "AdminAccountDeletion",
        components: {
            default: AccountDeletionForm,
            navbar: TheAdminNavbar,
            sidebar: TheAdminSidebar,
        }
    },
    {
        path: "/admin/profile",
        name: "AdminProfileView",
        components: {
            default: ProfileView,
            navbar: TheAdminNavbar,
            sidebar: TheAdminSidebar,
        }
    },
    {
        path: "/fishingInstructor",
        name: "FishingInstructorDashboard",
        components: {
            default: FishingInstructorPage,
            navbar: TheAdminNavbar,
            sidebar: TheAdminSidebar
        }
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router