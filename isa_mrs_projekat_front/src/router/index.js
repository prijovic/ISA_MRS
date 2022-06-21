import { createRouter, createWebHistory } from "vue-router";
import ProfileView from "@/components/GeneralComponents/UserAccountForms/ProfileView";
import TheAdminSidebar from "@/components/Administrator/AdminPage/TheAdminSidebar/TheAdminSidebar";
import AdminDashboard from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminDashboard";
import PasswordChangeForm from "@/components/GeneralComponents/UserAccountForms/PasswordChangeForm";
import AccountDeletionForm from "@/components/GeneralComponents/UserAccountForms/AccountDeletionForm";
import TheUnregisteredUserSidebar from "@/components/UnregisteredUser/TheUnregisteredUserSidebar";
import store from "@/store";
import TheFishingInstructorSidebar from "@/components/FishingInstructor/FishingInstructorPage/components/TheFishingInstructorSidebar";
import OwnerContentPage from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/OwnerContentPage/OwnerContentPage";
import TheOwnerSidebar from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/TheOwnerSidebar/TheOwnerSidebar";
import BoatOwnerContentPage from "@/components/BoatOwner/BoatOwnerPage/components/BoatOwnerMainView/BoatOwnerContentPage";
import TheBoatOwnerSidebar from "@/components/BoatOwner/BoatOwnerPage/components/TheBoatOwnerSidebar/TheBoatOwnerSidebar";
import AdminRequestsView from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRequestsView/AdminRequestsView";
import InitView from "@/components/UnregisteredUser/InitView";
import RegistrationPage from "@/components/UnregisteredUser/components/RegistrationPage";
import IncomeRateChangeForm
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminIncomeRateComponents/IncomeRateChangeForm";
import FishingInstructorAdventuresView
    from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorAllAdventuresView";
import CardMenu from "@/components/UnregisteredUser/components/CardMenu";
// import ClientMainView from "@/components/Client/ClientPage/components/ClientMainView";
import ClientSidebar from "@/components/Client/ClientPage/components/ClientSidebar/ClientSidebar";
import RentalProfile from "@/components/UnregisteredUser/components/Rental/RentalProfile";
import VerificationPage from "@/components/UnregisteredUser/components/VerificationPage";
import ClientProfile from "@/components/Client/ClientPage/components/ClientProfile/ClientProfile";
import AdminUsersView
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminUsersView";
import AdminRentalsView
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRentalsView";
import ReservationsCalendarView from "@/components/RentalObjectOwner/ReservationsCalendarView";
import ClientReservationsHistory
    from "@/components/Client/ClientPage/components/ClientProfile/ClientReservations/ClientReservationsHistory/ClientReservationsHistory";
import AdventureCreationPage
    from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/AdventureCreationPage";
import AllCottagesView
    from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/AllCottagesView/AllCottagesView";
import AllBoatsView from "@/components/BoatOwner/BoatOwnerPage/components/AllBoatsView/AllBoatsView";
import VacationRentalAdditionForm
    from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/VacationRentalAdditionForm";
import BoatAdditionForm from "@/components/BoatOwner/BoatOwnerPage/components/BoatAdditionForm";
import InstructorProfile from "@/components/UnregisteredUser/components/Instructor/InstructorProfile";
import DefineSpecialOffer from "@/components/RentalObjectOwner/DefineSpecialOffer";
import AdminReviewsView from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminReviewsView";
import AdminReportsView from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminReportsView";
import InstructorProfilePage
    from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorProfile/InstructorProfilePage";
import InstructorReservations
    from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorReservations/InstructorReservations";
import AdminFinancesView from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminFinancesView";
import LoyaltyProgramView
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminLoyaltyProgramComponents/LoyaltyProgramView";
import LoyaltyCategoryCreationPage
    from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminLoyaltyProgramComponents/LoyaltyCategoryCreationPage";
import BoatOwnerProfilePage
    from "@/components/BoatOwner/BoatOwnerPage/components/BoatOwnerMainView/BoatOwnerProfile/BoatOwnerProfilePage";
import VacationRentalOwnerProfilPage
    from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/VacationRentalOwnerProfile/VacationRentalOwnerProfilPage";
import BoatOwnerReservations
    from "@/components/BoatOwner/BoatOwnerPage/components/BoatOwnerMainView/BoatOwnerReservations/BoatOwnerReservations";
import VacationRentalOwnerReservations
    from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/VacationRentalOwnerReservations/VacationRentalOwnerReservations";
import EditPersonalData from "@/components/GeneralComponents/EditPersonalData";
//import InstructorCardView from "@/components/UnregisteredUser/components/InstructorCardView";



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
        path: "/admin/rentals",
        name: "AdminRentals",
        components: {
            default: AdminRentalsView,
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
        path: "/admin/reviews",
        name: "AdminReviews",
        components: {
            default: AdminReviewsView,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/reports",
        name: "AdminReports",
        components: {
            default: AdminReportsView,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/finances",
        name: "AdminFinances",
        components: {
            default: AdminFinancesView,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/loyaltyProgram",
        name: "AdminLoyaltyProgram",
        components: {
            default: LoyaltyProgramView,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/admin/loyaltyProgram/newCategory",
        name: "AdminNewLoyaltyCategory",
        components: {
            default: LoyaltyCategoryCreationPage,
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
        path: "/admin/:type-:id",
        name: "AdminRentalProfile",
        components: {
            default: RentalProfile,
            sidebar: TheAdminSidebar,
        },
        meta: {
            public: false
        },
    },
    {
        path: "/fishingInstructor",
        name: "FishingInstructorHome",
        components: {
            default: InstructorReservations,
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
            default: InstructorProfilePage,
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
        path: "/fishingInstructor/:type/:id",
        name: "FishingInstructorAdventure",
        components: {
            default: RentalProfile,
            sidebar: TheFishingInstructorSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/calendar",
        name: "FishingInstructorCalendar",
        components: {
            default: ReservationsCalendarView,
            sidebar: TheFishingInstructorSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/newAdventure",
        name: "AdventureAdding",
        components: {
            default: AdventureCreationPage,
            sidebar: TheFishingInstructorSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/updateAdventure/:id",
        name: "AdventureUpdate",
        components: {
            default: AdventureCreationPage,
            sidebar: TheFishingInstructorSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/defineSpecialOffer/:id",
        name: "SpecialOfferDefine",
        components: {
            default: DefineSpecialOffer,
            sidebar: TheFishingInstructorSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/fishingInstructor/Reservations",
        name: "InstructorReservations",
        components: {
            default: InstructorReservations,
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
        path: "/vacationRentalOwner/profile",
        name: "VacationRentalOwnerView",
        components: {
            default: VacationRentalOwnerProfilPage,
            sidebar: TheOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/cottages",
        name: "ViewCottages",
        components: {
            default: AllCottagesView,
            sidebar: TheOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/:type/:id",
        name: "OwnerVacationRental",
        components: {
            default: RentalProfile,
            sidebar: TheOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/newCottage",
        name: "AddNewVacationRental",
        components:{
            default: VacationRentalAdditionForm,
            sidebar: TheOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/Reservations",
        name: "VacationRentalUpdate",
        components: {
            default: InstructorReservations,
            sidebar: TheOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/Reservations",
        name: "VacationRentalOwnerReservations",
        components: {
            default: VacationRentalOwnerReservations,
            sidebar: TheOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/defineSpecialOffer/:id",
        name: "DefineSpecialOffer",
        components: {
            default: DefineSpecialOffer,
            sidebar: TheOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/calendar",
        name: "VacationRentalOwnerReservations",
        components: {
            default: ReservationsCalendarView,
            sidebar: TheOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/vacationRentalOwner/editPersonalData/:id",
        name: "VacationRentalOwnerEditPersonalData",
        components: {
            default: EditPersonalData,
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
        path: "/boatOwner/profile",
        name: "BoatOwnerView",
        components: {
            default: BoatOwnerProfilePage,
            sidebar: TheBoatOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/editPersonalData/:id",
        name: "BoatOwnerEditPersonalData",
        components: {
            default: EditPersonalData,
            sidebar: TheBoatOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/boats",
        name: "ViewBoats",
        components: {
            default: AllBoatsView,
            sidebar: TheBoatOwnerSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/:type/:id",
        name: "BoatOwnerBoat",
        components: {
            default: RentalProfile,
            sidebar: TheBoatOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/newBoat",
        name: "AddNewBoat",
        components:{
            default: BoatAdditionForm,
            sidebar: TheBoatOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/updateBoat/:id",
        name: "BoatUpdate",
        components: {
            default: BoatAdditionForm,
            sidebar: TheBoatOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/defineSpecialOffer/:id",
        name: "SpecialOfferDefine",
        components: {
            default: DefineSpecialOffer,
            sidebar: TheBoatOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/Reservations",
        name: BoatOwnerReservations,
        components: {
            default: BoatOwnerReservations,
            sidebar: TheBoatOwnerSidebar
        },
        meta: {
            public: false
        }
    },
    {
        path: "/boatOwner/calendar",
        name: "BoatOwnerReservations",
        components: {
            default: ReservationsCalendarView,
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
            default: ClientProfile,
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
        path: "/client/:type/all",
        name: "ClientRentals",
        components: {
            default: CardMenu,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        }
    },
    {
        path: "/client/Instructors",
        name: "InstructorCardView",
        components: {
            default: CardMenu,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        },
    },
    {
        path: "/client/InstructorProfile/:id",
        name: "InstructorProfile",
        components: {
            default: InstructorProfile,
            sidebar: ClientSidebar,
        },
        meta: {
            public: false
        },
    },
    {
        path: "/client/RentalProfile/:type-:id",
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
    {
        path: "/client/ReservationsHistory",
        name: "ClientReservationsHistory",
        components: {
            default: ClientReservationsHistory,
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