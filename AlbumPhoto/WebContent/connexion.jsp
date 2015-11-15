<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
    </head>
    <body>
        <form method="post" action="Connexion">
            <fieldset>
                <legend>Connexion</legend>
                <p>Vous pouvez vous connecter via ce formulaire.</p>

                <label for="login">Login <span class="requis">*</span></label>
                <input type="text" id="login" name="login" value="<c:out value="${utilisateur.login}"/>" size="20" maxlength="60" />
                <br />

                <label for="pass">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="pass" name="pass" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['login/pass']}</span>
                <br />

                <input type="submit" value="Connexion" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
    </body>
</html>