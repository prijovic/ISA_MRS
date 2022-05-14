import {createStore} from "vuex";

const getDefaultState = () => {
  return {
      user: null,
      email: null,
      access_token: null,
      rentalId: null,
      rentalType: null
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
        rentalId: (state) => {
            return state.rentalId;
        },
        rentalType: (state) => {
            return state.rentalType;
        },
        isAuthenticated: (state) => {
            return state.access_token != null;
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
        rentalId(context, rentalId) {
            context.commit("rentalId", rentalId);
        },
        rentalType(context, rentalType) {
            context.commit("rentalType", rentalType);
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
        rentalId(state, rentalId) {
            state.rentalId = rentalId;
        },
        rentalType(state, rentalType) {
            state.rentalType = rentalType;
        },
        reset_state(state) {
            Object.assign(state, getDefaultState());
        }
    }
});

export default store;
