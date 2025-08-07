-- ROLES
insert into roles(rolename)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_SUPERVISOR');


-- USERS
INSERT INTO public.users (username, enabled, password)
VALUES ('karelde2e', true, '$2a$10$eXvT4rWfJ.tYOBzaHNqduuZBLOnPlgl3AUY/KlrrITupluQIXE6V6');
INSERT INTO public.users (username, enabled, password)
VALUES ('anssiepansie', true, '$2a$10$GoRaNiFr/hIwtdyHAkow3OHmJuz.yg8IUDQx4.MWyFCDPBuBzRGda');

INSERT INTO public.user_roles (rolename, username)
VALUES ('ROLE_USER', 'karelde2e');
INSERT INTO public.user_roles (rolename, username)
VALUES ('ROLE_USER', 'anssiepansie');
INSERT INTO public.user_roles (rolename, username)
VALUES ('ROLE_ADMIN', 'anssiepansie');

-- PROFILES
INSERT INTO public.profiles (id_profile, gender, firstname, lastname, email, username)
VALUES (1, 'female', 'Ans', 'Pans', 'ansiepansie@gmail.com', 'anssiepansie');
INSERT INTO public.profiles (id_profile, gender, firstname, lastname, email, username)
VALUES (2, 'male', 'Karel', 'Grote', 'newMail@gmail.com', 'karelde2e');


-- NOTIFICATIONS
INSERT INTO public.notifications (pinned, created, id_notification, title, subtitle, content)
VALUES (true, '2023-09-21 09:30:00.000000', 1, 'De Smaken van Italië', 'Een culinaire reis door Bella Italia',  '<h2>
  Hi there,
</h2>
<p>
  this is a <em>basic</em> example of <strong>Tiptap</strong>. Sure, there are all kind of basic text styles you’d probably expect from a text editor. But wait until you see the lists:
</p>
<ul>
  <li>
    That’s a bullet list with one …
  </li>
  <li>
    … or two list items.
  </li>
</ul>
<p>
  Isn’t that great? And all of that is editable. But wait, there’s more. Let’s try a code block:
</p>
<pre><code class="language-css">body {
  display: none;
}</code></pre>
<p>
  I know, I know, this is impressive. It’s only the tip of the iceberg though. Give it a try and click a little bit around. Don’t forget to check the other examples too.
</p>
<blockquote>
  Wow, that’s amazing. Good work, boy! 👏
  <br />
  — Mom
</blockquote>');

