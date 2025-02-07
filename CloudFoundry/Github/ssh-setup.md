#   Github SSH Setup


```bash
ssh-keygen -t ed25519 -C "gabriel.vr.freire@gmail.com"
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519
cat ~/.ssh/id_ed25519.pub
```

Lab2dev Trial Backend Developer Space