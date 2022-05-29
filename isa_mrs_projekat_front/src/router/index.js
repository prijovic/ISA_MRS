import { createRouter, createWebHistory } from "vue-router";
import ProfileView from "@/components/GeneralComponents/UserAccountForms/ProfileView";
import TheAdminSidebar from "@/components/Administrator/AdminPage/TheAdminSidebar/TheAdminSidebar";
import AdminDashboard from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminDashboard";
import PasswordChangeForm from "@/components/GeneralComponents/UserAccountForms/PasswordChangeForm";
import FishingInstructorPage from "@/components/FishingInstructor/FishingInstructorPage/FishingInstructorPage";
import AccountDeletionForm from "@/components/GeneralComponents/UserAccountForms/AccountDeletionForm";
import TheUnregisteredUserSidebar from "@/components/UnregisteredUser/TheUnregisteredUserSidebar";
import store from "@/store";
import TheFishingInstructorSidebar from "@/components/FishingInstructor/FishingInstructorPage/components/TheFishingInstructorSidebar/TheFishingInstructorSidebar";
import OwnerContentPage from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/OwnerContentPage/OwnerContentPage";
import TheOwnerSidebar from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/TheOwnerSidebar/TheOwnerSidebar";
import BoatOwnerContentPage from "@/components/BoatOwner/BoatOwnerPage/components/BoatOwnerMainView/BoatOwnerContentPage";
import TheBoatOwnerSidebar from "@/components/BoatOwner/BoatOwnerPage/components/TheBoatOwnerSidebar/TheBoatOwnerSidebar";
import AdminRequestsView from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRequestsView/AdminRequestsView";
import InitView from "@/components/UnregisteredUser/InitView";
import RegistrationPage from "@/components/UnregisteredUser/components/RegistrationPage";
import IncomeRateChangeForm
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminIncomeIncomeRateComponents/IncomeRateChangeForm";
import FishingInstructorAdventuresView
    from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorAllAdventuresView/InstructorAllAdventuresView";
import FishingInstructorAdventureProfileView
    from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorAllAdventuresView/InstructorAdventureProfile/InstructorAdventureProfile";
import CardMenu from "@/components/UnregisteredUser/components/CardMenu";
import ClientMainView from "@/components/Client/ClientPage/components/ClientMainView";
import ClientSidebar from "@/components/Client/ClientPage/components/ClientSidebar/ClientSidebar";
import RentalProfile from "@/components/UnregisteredUser/components/RentalProfile";
import VerificationPage from "@/components/UnregisteredUser/components/VerificationPage";
import ClientProfile from "@/components/Client/ClientPage/components/ClientProfile/ClientProfile";
import AdminUsersView
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminUsersView/AdminUsersView";


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
        path: "/token:token/refresh:refresh",
        name: "Verification",
        components: {
            default: VerificationPage,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        }
    },
    {
        path: "/registration",
        name: "Registration",
        components: {
            default: RegistrationPage,
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
        path: "/admin/users",
        name: "AdminUsers",
        components: {
            default: AdminUsersView,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/newUser",
        name: "AdminAdding",
        components: {
            default: RegistrationPage,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/user/:id",
        name: "AdminUserView",
        components: {
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
            default: CardMenu,
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
        path: "/admin/requests",
        name: "AdminRequests",
        components: {
            default: AdminRequestsView,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/incomeRates",
        name: "AdminIncomeRates",
        components: {
            default: IncomeRateChangeForm,
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
        path: "/fishingInstructor/changePass",
        name: "FishingInstructorPassword",
        components: {
            default: PasswordChangeForm,
            sidebar: TheFishingInstructorSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/delAcc",
        name: "FishingInstructorDeletion",
        components: {
            default: AccountDeletionForm,
            sidebar: TheFishingInstructorSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/profile",
        name: "FishingInstructorView",
        components: {
            default: ProfileView,
            sidebar: TheFishingInstructorSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/adventures",
        name: "FishingInstructorAdventures",
        components: {
            default: FishingInstructorAdventuresView,
            sidebar: TheFishingInstructorSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/adventure",
        name: "FishingInstructorAdventure",
        components: {
            default: FishingInstructorAdventureProfileView,
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
            public: true
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
    },
    {
        path: "/vacationRentalOwner/delAcc",
        name: "VacationRentalOwnerAccountDeletion",
        components: {
            default: AccountDeletionForm,
            sidebar: TheOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/delAcc",
        name: "BoatOwnerAccountDeletion",
        components: {
            default: AccountDeletionForm,
            sidebar: TheBoatOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/client",
        name: "Client",
        components: {
            default: ClientMainView,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/client/changePass",
        name: "ClientChangePassword",
        components: {
            default: PasswordChangeForm,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/client/vacationRentals",
        name: "ClientVacationRentals",
        components: {
            default: CardMenu,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/client/boats",
        name: "ClientBoats",
        components: {
            default: CardMenu,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/client/adventures",
        name: "ClientAdventures",
        components: {
            default: CardMenu,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/client/RentalProfile",
        name: "ClientRentalProfile",
        components: {
            default: RentalProfile,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        },
    },
    {
        path: "/client/Profile",
        name: "ClientProfile",
        components: {
            default: ClientProfile,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        },
    },
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    {
        path: "/vacationRentals",
        name: "VacationRentals",
        components: {
            default: CardMenu,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        }
    },
    {
        path: "/boats",
        name: "Boats",
        components: {
            default: CardMenu,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        }
    },
    {
        path: "/adventures",
        name: "Adventures",
        components: {
            default: CardMenu,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        }
    },
    {
        path: "/RentalProfile",
        name: "RentalProfile",
        components: {
            default: RentalProfile,
            sidebar: TheUnregisteredUserSidebar,
        },
        meta: {
            disableIfLoggedIn: true,
            public: true
        },
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
   if (!to.meta.public) {
       if (store.getters.isAuthenticated) {
           let redirect_path = "/" + store.getters.user + "/changePass";
           if (from.path !== redirect_path && store.getters.first_login && from.path !== "/") {
               next({path:redirect_path});
           } else if (from.path === redirect_path && store.getters.first_login) {
               /* pass */
           } else {
               next();
           }
       } else {
           next({path: "/"})
       }
   } else {
       next();
   }
});


export default router;