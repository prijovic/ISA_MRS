import { createRouter, createWebHistory } from "vue-router";
import ProfileView from "@/components/GeneralComponents/UserAccountForms/ProfileView";
import TheAdminSidebar from "@/components/Administrator/AdminPage/components/TheAdminSidebar/TheAdminSidebar";
import AdminDashboard from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminDashboard";
import PasswordChangeForm from "@/components/GeneralComponents/UserAccountForms/PasswordChangeForm";
import FishingInstructorPage from "@/components/FishingInstructor/FishingInstructorPage/FishingInstructorPage";
import AccountDeletionForm from "@/components/GeneralComponents/UserAccountForms/AccountDeletionForm";
import InitView from "@/components/UnregisteredUser/InitView";
import TheUnregisteredUserSidebar from "@/components/UnregisteredUser/TheUnregisteredUserSidebar";
import store from "@/store";
import TheFishingInstructorSidebar from "@/components/FishingInstructor/FishingInstructorPage/components/TheFishingInstructorSidebar/TheFishingInstructorSidebar";
import OwnerContentPage from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/OwnerContentPage/OwnerContentPage";
import TheOwnerSidebar from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/TheOwnerSidebar/TheOwnerSidebar";
import BoatOwnerContentPage from "@/components/BoatOwner/BoatOwnerPage/components/BoatOwnerMainView/BoatOwnerContentPage";
import TheBoatOwnerSidebar from "@/components/BoatOwner/BoatOwnerPage/components/TheBoatOwnerSidebar/TheBoatOwnerSidebar";
import CardView from "@/components/UnregisteredUser/components/CardView";

const routes = [
    {
        path: "/",
        name: "InitHome",
        components: {
            default: InitView,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        }
    },
    {
        path: "/admin",
        name: "AdminDashboard",
        components: {
            default: AdminDashboard,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentals",
        name: "VacationRentals",
        components: {
            default: CardView,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        }
    },
    {
        path: "/admin/changePass",
        name: "AdminChangePassword",
        components: {
            default: PasswordChangeForm,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/delAcc",
        name: "AdminAccountDeletion",
        components: {
            default: AccountDeletionForm,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/profile",
        name: "AdminProfileView",
        components: {
            default: ProfileView,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor",
        name: "FishingInstructorHome",
        components: {
            default: FishingInstructorPage,
            sidebar: TheFishingInstructorSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner",
        name: "VacationRentalOwnerHome",
        components: {
            default: OwnerContentPage,
            sidebar: TheOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/changePass",
        name: "VacationRentalOwnerChangePassword",
        components: {
            default: PasswordChangeForm,
            sidebar: TheOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner",
        name: "BoatOwnerHome",
        components: {
            default: BoatOwnerContentPage,
            sidebar: TheBoatOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/changePass",
        name: "BoatOwnerChangePassword",
        components: {
            default: PasswordChangeForm,
            sidebar: TheBoatOwnerSidebar,
        },
        meta: {
            public: false
        }
    }
];


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
   if (!to.meta.public) {
       if (store.getters.isAuthenticated) {
           next();
       } else {
           next({path: "/"})
       }
   } else {
       next();
   }
});

export default router;