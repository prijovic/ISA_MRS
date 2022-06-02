import { ref, computed} from "vue";

export const collapsed = ref(false);
export const toggleSidebar = () => (collapsed.value = !collapsed.value)

export const SIDEBAR_WIDTH = 210;
export const SIDEBAR_WIDTH_COLLAPSED = 41;
export const sidebarWidth = computed( () => `${collapsed.value ? SIDEBAR_WIDTH_COLLAPSED : SIDEBAR_WIDTH}px`);


export const LOADING = ref(false);
export const toggleLoading = () => (LOADING.value = !LOADING.value)
export const loading = computed( () => LOADING.value);


export const PROCESSING = ref(false);
export const toggleProcessing = () => (PROCESSING.value = !PROCESSING.value)
export const processing = computed( () =>
{
    window.scrollTo(0, 0);
    if (PROCESSING.value) {
        document.body.style.overflowY = 'hidden';
    } else {
        document.body.style.overflowY = 'auto';
    }
    return PROCESSING.value
});
