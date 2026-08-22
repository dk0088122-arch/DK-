/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        warrior: {
          blue: '#0061A4',
          'blue-dark': '#00487D',
          'blue-deep': '#002E54',
          'blue-container': '#D1E4FF',
          'blue-border': '#B0C9E8',
          gold: '#FFB703',
          'gold-dark': '#FB8500',
          'bg-light': '#F6F9FD',
          'surface-light': '#FFFFFF',
          'text-primary': '#191C1E',
          'text-secondary': '#42474E',
          'text-muted': '#72777F',
          'divider': '#E0E3E8',
          'success-green': '#198754',
          'success-container': '#D1E7DD',
          'error-red': '#DC3545',
          'error-container': '#F8D7DA'
        }
      }
    },
  },
  plugins: [],
}
