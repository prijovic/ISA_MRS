import {createStore} from "vuex";

const getDefaultState = () => {
  return {
      user: null,
      email: null,
      access_token: null,
      first_login: null,
      rentalId: null,
      rentalType: null,
      isSuperAdmin: null,
  }
};

const state = getDefaultState();

const store = createStore({
    state,
    getters: {
        user: (state) => {
            return state.user;
        },
        email: (state) => {
            return state.email;
        },
        access_token: (state) => {
            return state.access_token;
        },
        first_login: (state) => {
            return state.first_login;
        },
        rentalId: (state) => {
            return state.rentalId;
        },
        rentalType: (state) => {
            return state.rentalType;
        },
        grade: (state) => {
            return state.grade;
        },
        isSuperAdmin: (state) => {
            return state.isSuperAdmin;
        },
        isAuthenticated: (state) => {
            return state.email != null;
        }
    },
    actions: {
        user(context, user) {
            context.commit("user", user);
        },
        email(context, email) {
            context.commit("email", email);
        },
        access_token(context, access_token) {
            context.commit("access_token", access_token);
        },
        first_login(context, first_login) {
            context.commit("first_login", first_login);
        },
        rentalId(context, rentalId) {
            context.commit("rentalId", rentalId);
        },
        rentalType(context, rentalType) {
            context.commit("rentalType", rentalType);
        },
        grade(context, grade) {
            context.commit("grade", grade);
        },
        isSuperAdmin(context, isSuperAdmin) {
            context.commit("isSuperAdmin", isSuperAdmin);
        },
        reset_state(context) {
            context.commit("reset_state")
        }
    },
    mutations: {
        user(state, user) {
            state.user = user;
        },
        email(state, email) {
            state.email = email;
        },
        access_token(state, access_token) {
            state.access_token = access_token;
        },
        first_login(state, first_login) {
            state.first_login = first_login;
        },
        rentalId(state, rentalId) {
            state.rentalId = rentalId;
        },
        rentalType(state, rentalType) {
            state.rentalType = rentalType;
        },
        grade(state, grade) {
            state.grade = grade;
        },
        isSuperAdmin(state, isSuperAdmin) {
            state.isSuperAdmin = isSuperAdmin;
        },
        reset_state(state) {
            Object.assign(state, getDefaultState());
        }
    }
});

export default store;
