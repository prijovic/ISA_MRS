import {createStore} from "vuex";

const state = {
    user: null,
    email: null,
    access_token: null,
};

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
        }
    }
});

export default store;
