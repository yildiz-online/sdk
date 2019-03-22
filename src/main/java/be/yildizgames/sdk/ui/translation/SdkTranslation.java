/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.sdk.ui.translation;

import be.yildizgames.common.client.translation.FrenchEnglishLanguageProvider;
import be.yildizgames.common.client.translation.Translation;
import be.yildizgames.common.client.translation.TranslationKey;
import be.yildizgames.common.util.language.LanguageValue;

/**
 * @author Grégory Van den Borre
 */
public class SdkTranslation extends FrenchEnglishLanguageProvider {

    public SdkTranslation() {
        super();
        Translation.getInstance().addLanguage(LanguageValue.EN, this);
        Translation.getInstance().addLanguage(LanguageValue.FR, this);
        Translation.getInstance().chooseLanguage(LanguageValue.FR);
        this.add("menu-file", "Fichier", "File");
        this.add("menu-new", "Nouveau", "New");
        this.add("menu-open", "Ouvrir...", "Open...");
        this.add("menu-save", "Sauvegarder", "Save");
        this.add("menu-create", "Créer", "Create");
        this.add("menu-particle_system", "Système de particules", "Particle system");
        this.add("menu-box", "Boite", "Box");
        this.add("create_project", "Créer un nouveau projet", "Create a new project");
        this.add("create_project-name", "Nom du projet", "Project name");
        this.add("create_project-author", "Auteur du projet", "Project author");
        this.add("create_project-group", "Groupe du projet", "Project group");
        this.add("create_project-licence", "Licence du projet", "Project licence");
    }

    public final String menuNew() {
        return Translation.getInstance().translate(TranslationKey.get("menu-new"));
    }

    public final String menuOpen() {
        return Translation.getInstance().translate(TranslationKey.get("menu-open"));
    }

    public final String menuSave() {
        return Translation.getInstance().translate(TranslationKey.get("menu-save"));
    }

    public final String menuFile() {
        return Translation.getInstance().translate(TranslationKey.get("menu-file"));
    }

    public final String menuCreate() {
        return Translation.getInstance().translate(TranslationKey.get("menu-create"));
    }

    public final String create() {
        return Translation.getInstance().translate(TranslationKey.get("menu-create"));
    }

    public final String menuParticleSystem() {
        return Translation.getInstance().translate(TranslationKey.get("menu-particle_system"));
    }

    public final String menuBox() {
        return Translation.getInstance().translate(TranslationKey.get("menu-box"));
    }

    public String createProject() {
        return Translation.getInstance().translate(TranslationKey.get("create_project"));
    }

    public String createProjectName() {
        return Translation.getInstance().translate(TranslationKey.get("create_project-name"));
    }

    public String createProjectAuthor() {
        return Translation.getInstance().translate(TranslationKey.get("create_project-author"));
    }

    public String createProjectGroup() {
        return Translation.getInstance().translate(TranslationKey.get("create_project-group"));
    }

    public String createProjectLicence() {
        return Translation.getInstance().translate(TranslationKey.get("create_project-licence"));
    }
}
