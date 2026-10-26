// @ts-check
import withNuxt from './.nuxt/eslint.config.mjs';
import betterTailwindcss from 'eslint-plugin-better-tailwindcss';
import { getDefaultAttributes } from 'eslint-plugin-better-tailwindcss/api/defaults';

export default withNuxt(
    betterTailwindcss.configs['correctness-error'],
    {
        rules: {
            // we decide case-by-case it a trailing comma makes sense or not
            '@stylistic/comma-dangle': 'off',

            // No need to go vertical when there is enough space, e. g. for small attributes
            'vue/max-attributes-per-line': ['error',
                { singleline: 3 }
            ]
        },
        settings: {
            'better-tailwindcss': {
                entryPoint: 'app/assets/css/main.css',
                attributes: [
                    ...getDefaultAttributes(),
                    ['^v-bind:ui$', [{ match: 'objectValues' }]]
                ]
            }
        }
    }
);
